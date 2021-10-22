# Remote Preferences

This package allows you to save key-value and use native backup system.

Android documentation: https://developer.android.com/guide/topics/data/keyvaluebackup#:~:text=Android%20Backup%20Service%20provides%20cloud,to%20the%20device's%20backup%20transport.

IOS documentation: https://developer.apple.com/documentation/foundation/nsubiquitouskeyvaluestore


### Available methods

```dart
Future<String?> get({required String key});

Future<void> save({required String key, required String? value});

Future<void> delete({required String key});

Future<void> deleteAll();
```