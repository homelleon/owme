package outworldmind.owme.tool;

public class DstSrcOutPipe {
	
	private Object inputDestination;
	private Object inputSource;
	private Object outputValue;
	
	public DstSrcOutPipe(Object inputDst, Object inputSrc, Object output) {
		inputDestination = inputDst;
		inputSource = inputSrc;
		outputValue = output;		
	}
	
	public Object getInputDst() {
		return inputDestination;
	}
	
	public Object getInputSrc() {
		return inputSource;
	}
	
	public Object getOutput() {
		return outputValue;
	}

}
