package calulator.server;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void sum(SumRequest sumRequest, StreamObserver<SumResponse> sumResponseStreamObserver) {
        long result = sumRequest.getFirstOperand() + sumRequest.getSecondOperand();
        SumResponse sumResponse = SumResponse.newBuilder()
                .setResult(result)
                .build();
        sumResponseStreamObserver.onNext(sumResponse);
        sumResponseStreamObserver.onCompleted();
    }

}
