package sense.full.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.13.1)",
    comments = "Source: cochlear_sense.proto")
public final class SenseGrpc {

  private SenseGrpc() {}

  public static final String SERVICE_NAME = "sense.full.v1.Sense";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<CochlearSense.Request,
      CochlearSense.Response> getSenseMethod;

  public static io.grpc.MethodDescriptor<CochlearSense.Request,
      CochlearSense.Response> getSenseMethod() {
    io.grpc.MethodDescriptor<CochlearSense.Request, CochlearSense.Response> getSenseMethod;
    if ((getSenseMethod = SenseGrpc.getSenseMethod) == null) {
      synchronized (SenseGrpc.class) {
        if ((getSenseMethod = SenseGrpc.getSenseMethod) == null) {
          SenseGrpc.getSenseMethod = getSenseMethod = 
              io.grpc.MethodDescriptor.<CochlearSense.Request, CochlearSense.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "sense.full.v1.Sense", "sense"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CochlearSense.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CochlearSense.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new SenseMethodDescriptorSupplier("sense"))
                  .build();
          }
        }
     }
     return getSenseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CochlearSense.RequestStream,
      CochlearSense.Response> getSenseStreamMethod;

  public static io.grpc.MethodDescriptor<CochlearSense.RequestStream,
      CochlearSense.Response> getSenseStreamMethod() {
    io.grpc.MethodDescriptor<CochlearSense.RequestStream, CochlearSense.Response> getSenseStreamMethod;
    if ((getSenseStreamMethod = SenseGrpc.getSenseStreamMethod) == null) {
      synchronized (SenseGrpc.class) {
        if ((getSenseStreamMethod = SenseGrpc.getSenseStreamMethod) == null) {
          SenseGrpc.getSenseStreamMethod = getSenseStreamMethod = 
              io.grpc.MethodDescriptor.<CochlearSense.RequestStream, CochlearSense.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "sense.full.v1.Sense", "sense_stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CochlearSense.RequestStream.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CochlearSense.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new SenseMethodDescriptorSupplier("sense_stream"))
                  .build();
          }
        }
     }
     return getSenseStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SenseStub newStub(io.grpc.Channel channel) {
    return new SenseStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SenseBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SenseBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SenseFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SenseFutureStub(channel);
  }

  /**
   */
  public static abstract class SenseImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<CochlearSense.Request> sense(
        io.grpc.stub.StreamObserver<CochlearSense.Response> responseObserver) {
      return asyncUnimplementedStreamingCall(getSenseMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CochlearSense.RequestStream> senseStream(
        io.grpc.stub.StreamObserver<CochlearSense.Response> responseObserver) {
      return asyncUnimplementedStreamingCall(getSenseStreamMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSenseMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                CochlearSense.Request,
                CochlearSense.Response>(
                  this, METHODID_SENSE)))
          .addMethod(
            getSenseStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                CochlearSense.RequestStream,
                CochlearSense.Response>(
                  this, METHODID_SENSE_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class SenseStub extends io.grpc.stub.AbstractStub<SenseStub> {
    private SenseStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SenseStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SenseStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SenseStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CochlearSense.Request> sense(
        io.grpc.stub.StreamObserver<CochlearSense.Response> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getSenseMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CochlearSense.RequestStream> senseStream(
        io.grpc.stub.StreamObserver<CochlearSense.Response> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSenseStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class SenseBlockingStub extends io.grpc.stub.AbstractStub<SenseBlockingStub> {
    private SenseBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SenseBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SenseBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SenseBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class SenseFutureStub extends io.grpc.stub.AbstractStub<SenseFutureStub> {
    private SenseFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SenseFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SenseFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SenseFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SENSE = 0;
  private static final int METHODID_SENSE_STREAM = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SenseImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SenseImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SENSE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sense(
              (io.grpc.stub.StreamObserver<CochlearSense.Response>) responseObserver);
        case METHODID_SENSE_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.senseStream(
              (io.grpc.stub.StreamObserver<CochlearSense.Response>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SenseBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SenseBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return CochlearSense.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Sense");
    }
  }

  private static final class SenseFileDescriptorSupplier
      extends SenseBaseDescriptorSupplier {
    SenseFileDescriptorSupplier() {}
  }

  private static final class SenseMethodDescriptorSupplier
      extends SenseBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SenseMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SenseGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SenseFileDescriptorSupplier())
              .addMethod(getSenseMethod())
              .addMethod(getSenseStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
