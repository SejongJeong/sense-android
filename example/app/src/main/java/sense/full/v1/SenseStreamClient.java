package sense.full.v1;

import com.google.protobuf.ByteString;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import sense.full.v1.Sense.RequestStream;
import sense.full.v1.Sense.Response;
import sense.full.v1.SenseGrpc.SenseStub;


public class SenseStreamClient {
	private static final long deadline = 10;
	
	ManagedChannel channel;
	SenseStub asyncStub;
	CountDownLatch finishLatch;
	StreamObserver<RequestStream> requestObserver;

	private SenseResultListener senseResultListener = null;
	
	private String apiKey;
	private int fs;
	private int bitrate;
	private boolean big_endian;
	private boolean signed;
	private String task;


	public SenseStreamClient(String apiKey) {
		this("34.80.243.56", 50051, apiKey);
	}
	private SenseStreamClient(String host, int port, String apiKey) {
	  this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
		this.apiKey = apiKey;
	}

	/** Construct client for accessing RouteGuide server using the existing channel. */
	private SenseStreamClient(ManagedChannelBuilder<?> channelBuilder) {
		super();
		channel = channelBuilder.build();
		asyncStub = SenseGrpc.newStub(channel);
	}





	public void end() {

		requestObserver.onCompleted();
	}

	private short[] b2s(byte[] barr) {
		short[] shorts = new short[barr.length / 2];
		if(this.big_endian)
			ByteBuffer.wrap(barr).order(ByteOrder.BIG_ENDIAN).asShortBuffer().get(shorts);
		else
			ByteBuffer.wrap(barr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
		return shorts;
	}

	private float[] s2f (short[] sarr) {
		float[] floats = new float[sarr.length];
		for(int i=0; i<sarr.length; i++) {
			float f;
			int r = sarr[i];
			f = ((float) r) / (float) 32768;
			if( f > 1 ) f = 1;
			if( f < -1 ) f = -1;
			floats[i] = f;
		}
		return floats;
	}

	private byte[] f2b (float[] farr) {
		byte[] bytes = new byte[farr.length*4];
		ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().put(farr);
		return bytes;
	}

	/*
	private byte[] int16Tofloat32(byte[] bytes){
		return f2b(s2f(b2s(bytes)));
	}
	*/



	public void pushShort(short[] shorts) throws Exception{
		pushFloat32(s2f(shorts));
	}

	public void pushFloat32(float[] floats) throws Exception{
		pushByte(f2b(floats));
	}



	public void pushByte(byte[] bytes) throws Exception{
		if(this.senseResultListener ==null){
			throw new Exception("Listener not registered.");
		}
		RequestStream _input = RequestStream.newBuilder()
				.setData(ByteString.copyFrom(bytes))
				.setTask(task)
				.setApikey(apiKey)
				.setDtype("float32")
				.setSr(fs)
				.build();
		requestObserver.onNext(_input);
	}


	public void senseStream(String task, int fs) throws Exception{
		this.big_endian = false;
		this.signed = false;
		this.bitrate = 32;

		this.fs = fs;
		this.task = task;

		//TODO:Verify Sample Rate




		finishLatch= new CountDownLatch(1);

		class BistreamObserver implements StreamObserver<Response>{
			@Override
			public void onNext(Response out) {

				//Log.d("CochlearSenseResult", out.getOutputs());
				senseResultListener.onResult(out.getOutputs());

			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.fromThrowable(t);

				senseResultListener.onError("ERROR : "+status);
				finishLatch.countDown();
			}

			@Override
			public void onCompleted() {
				senseResultListener.onComplete();
				finishLatch.countDown();

			}
		}
		requestObserver = asyncStub.withDeadlineAfter(deadline, TimeUnit.MINUTES)
				.senseStream(new BistreamObserver());



	}

	public void setListener(SenseResultListener listener){
		this.senseResultListener = listener;
	}
	
}
