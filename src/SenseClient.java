package sense.full.v1;

import com.google.protobuf.ByteString;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import sense.full.v1.Sense.Request;
import sense.full.v1.Sense.Response;
import sense.full.v1.SenseGrpc.SenseStub;


public class SenseClient {
	private static final int blockSize = 2*1024*1024;
	private final ManagedChannel channel;
	private final SenseStub asyncStub;
	

	
	private String apiKey;

	public static enum ServiceType {
		//Non stream service
		event,
		speech,
		music
	}
	
	public SenseClient(String apiKey) {
		/**
		 * Default connection to sense.cochlear.ai:9000
		 * To get an apikey, contact Cochlear.ai
		 */
		this("sense.cochlear.ai", 50051, apiKey);
	}

	private SenseClient(String host, int port, String apiKey) {
		/**
		 * You can designate any host and port. Probably should be blocked on release.  
		 */
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
		this.apiKey = apiKey;
	}

	private SenseClient(ManagedChannelBuilder<?> channelBuilder) {
		/**
		 * GRPC default constructor. Private call only.
		 */
		channel = channelBuilder.build();
		asyncStub = SenseGrpc.newStub(channel);
	}
	
	
	

	
	
	public String sense(String task, InputStream is, String format) throws Exception {
		
		final String[] result = new String[1];
		final CountDownLatch finishLatch = new CountDownLatch(1);
		StreamObserver<Response> responseObserver = new StreamObserver<Response>() {
			
		    @Override
		    public void onNext(Response out) {
		    	result[0] = out.getOutputs();
		    }

		    @Override
		    public void onError(Throwable t) {
		    	Status status = 	Status.fromThrowable(t);
		    	System.err.println(status);
		    	finishLatch.countDown();
		    }

		    @Override
		    public void onCompleted() {
		    	finishLatch.countDown();
		    }
		};
		StreamObserver<Request> requestObserver;
		requestObserver= asyncStub.sense(responseObserver);

		try {
			
			byte[] buffer = new byte[blockSize];
			while(is.read(buffer)>0) {
				Request _input = Request.newBuilder()
						.setData(ByteString.copyFrom(buffer))
						.setTask(task)
						.setApikey(this.apiKey)
						.setFormat(format)
						.build();
				requestObserver.onNext(_input);
			}
			
		}catch(RuntimeException e) {
			requestObserver.onError(e);
			throw e;
		}
		requestObserver.onCompleted();
		finishLatch.await(1,TimeUnit.MINUTES);
		return result[0];
	}


}