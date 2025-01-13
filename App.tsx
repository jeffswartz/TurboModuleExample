import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  Text,
  TextInput,
  Button,
  EventSubscription,
} from 'react-native';

import NativeLocalStorage from './specs/NativeLocalStorage';

const EMPTY = '<empty>';

function App(): React.JSX.Element {
  const apiKey = '472032';
  const sessionId = '1_MX40NzIwMzJ-fjE3MzM0NTAzOTcyNjh-L0FQMkR0K2tVc214ajJOVzZiYWtYclg1fn5-';
  const token = 'T1==cGFydG5lcl9pZD00NzIwMzImc2lnPTQ4YzFiYjUyOWYzM2FiYTUxZTJkYjE5NmY5ODVmN2U2ZDdlZWU3YzY6c2Vzc2lvbl9pZD0xX01YNDBOekl3TXpKLWZqRTNNek0wTlRBek9UY3lOamgtTDBGUU1rUjBLMnRWYzIxNGFqSk9WelppWVd0WWNsZzFmbjUtJmNyZWF0ZV90aW1lPTE3MzYyNzkyOTEmbm9uY2U9MC4yMTI0Nzc0NzA1NTc3Mzc5JnJvbGU9bW9kZXJhdG9yJmV4cGlyZV90aW1lPTE3Mzg4NzEyOTA1NTcmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0=';

  // const listenerSubscription = React.useRef<null | EventSubscription>(null);
  const [value, setValue] = React.useState<string | null>(null);

  const [editingValue, setEditingValue] = React.useState<
    string | null
  >(null);

  React.useEffect(() => {
    const storedValue = NativeLocalStorage?.getItem('myKey');
    setValue(storedValue ?? '');

    NativeLocalStorage?.onStreamCreated((event: StreamEvent) => {
      console.log('onStreamCreated', event);
    });
    NativeLocalStorage?.onStreamDestroyed((event: StreamEvent) => {
      console.log('onStreamCreated', event);
    });
  }, []);

  function saveValue() {
    NativeLocalStorage?.setItem(editingValue ?? EMPTY, 'myKey');
    setValue(editingValue);
  }

  async function initSession() {
    NativeLocalStorage?.onSessionConnected((event: SessionConnectEvent) => {
      console.log('onSessionConnected', event);
    });

    NativeLocalStorage?.initSession(apiKey, sessionId, {});
    await NativeLocalStorage?.connect(sessionId, token);
      // .then(() => )
    console.log('connect() called');
    setValue(sessionId);
  }

  async function disconnect() {
    NativeLocalStorage?.onSessionDisconnected((event: SessionConnectEvent) => {
      console.log('onSessionDisconnected', event);
    });

    await NativeLocalStorage?.disconnect(sessionId);
      // .then(() => )
    console.log('disconnect() called');
    setValue(sessionId);
  }

  function clearAll() {
    NativeLocalStorage?.clear();
    setValue('');
  }

  function deleteValue() {
    NativeLocalStorage?.removeItem(editingValue ?? EMPTY);
    setValue('');
  }

  return (
    <SafeAreaView style={{flex: 1}}>
      <Text style={styles.text}>
        Current stored value is: {value ?? 'No Value'}
      </Text>
      <TextInput
        placeholder="Enter the text you want to store"
        style={styles.textInput}
        onChangeText={setEditingValue}
      />
      <Button title="Save" onPress={saveValue} />
      <Button title="initSession" onPress={initSession} />
      <Button title="disconnect" onPress={disconnect} />
      <Button title="Delete" onPress={deleteValue} />
      <Button title="Clear" onPress={clearAll} />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  text: {
    margin: 10,
    fontSize: 20,
  },
  textInput: {
    margin: 10,
    height: 40,
    borderColor: 'black',
    borderWidth: 1,
    paddingLeft: 5,
    paddingRight: 5,
    borderRadius: 5,
  },
});

export default App;
