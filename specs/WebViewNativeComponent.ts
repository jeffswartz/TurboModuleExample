import type {HostComponent, ViewProps} from 'react-native';
import type {BubblingEventHandler} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type StreamEvent = {
  streamId: string;
};

type StreamErrorEvent = {
  streamId: string;
  errorMessage: string;
};

export interface NativeProps extends ViewProps {
    sessionId: string;
    streamId: string;
    onSubscriberConnected?: BubblingEventHandler<StreamEvent> | null;
    onStreamDestroyed?: BubblingEventHandler<StreamEvent> | null;
    onSubscriberError?: BubblingEventHandler<StreamErrorEvent> | null;
    // subscribeToStream(streamId: string): Promise<string>;
}

export default codegenNativeComponent<NativeProps>(
  'CustomWebView',
) as HostComponent<NativeProps>;