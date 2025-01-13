import type {TurboModule} from 'react-native';
import {TurboModuleRegistry} from 'react-native';
import type {EventEmitter} from 'react-native/Libraries/Types/CodegenTypes';

type SessionOptions = {
  connectionEventsSuppressed?: boolean;
  ipWhitelist?: boolean;
};

export type SessionConnectEvent = {
  sessionId: string;
  connectionId: string;
};

export type StreamEvent = {
  streamId: string;
};

export type SignalEvent = {
  sessionId: string;
  connectionId: string;
  type: string;
  data: string;
}

export interface Spec extends TurboModule {
  setItem(value: string, key: string): void;
  getItem(key: string): string | null;
  removeItem(key: string): void;
  clear(): void;
  readonly onSessionConnected?: EventEmitter<SessionConnectEvent>;
  readonly onSessionDisconnected?: EventEmitter<SessionConnectEvent>;
  readonly onStreamCreated?: EventEmitter<StreamEvent>;
  readonly onStreamDestroyed?: EventEmitter<StreamEvent>;
  readonly onSignalReceived?: EventEmitter<SignalEvent>;
  initSession(apiKey: string, sessionId: string, options?: SessionOptions):void;
  connect(sessionId: string, token: string): Promise<void>;
  disconnect(sessionId: string): Promise<void>;
  sendSignal(sessionId: string, type: string, data: string): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>(
  'NativeLocalStorage',
);

