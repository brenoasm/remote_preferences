library remote_preferences;

import 'package:flutter/services.dart';

class RemotePreferences {
  static const MethodChannel _channel = MethodChannel('remote_preferences');

  Future<String?> get({required String key}) {
    return _channel.invokeMethod<String?>('get', {'key': key});
  }

  Future<void> save({required String key, required String? value}) {
    return _channel.invokeMethod('save', {'key': key, 'value': value});
  }

  Future<void> delete({required String key}) {
    return _channel.invokeMethod('delete', {'key': key});
  }

  Future<void> deleteAll() {
    return _channel.invokeMethod('deleteAll');
  }
}
