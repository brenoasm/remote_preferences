package io.remote_preferences;

import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class RemotePreferencesPlugin implements FlutterPlugin, MethodCallHandler {
    private Context context;
    private MethodChannel channel;

    static final String METHOD_CHANNEL_NAME = "remote_preferences";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), METHOD_CHANNEL_NAME);
        channel.setMethodCallHandler(this);

        this.context = flutterPluginBinding.getApplicationContext();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "get":
                onGet(call, result);
                break;
            case "save":
                onSave(call, result);
                break;
            case "delete":
                onDelete(call, result);
                break;
            case "deleteAll":
                onDeleteAll(result);
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    private void onGet(MethodCall call, Result result) {
        final String key = call.argument("key");

        Log.d("RemotePreferences", "Get called with key " + key);

        SharedPreferences prefs = context.getSharedPreferences(
                CustomBackupAgent.REMOTE_PREFERENCES_KEY, Context.MODE_PRIVATE);

        final String value = prefs.getString(key, "");

        if (value.isEmpty() || value == null) {
            result.success(null);
        }

        result.success(value);
    }

    private void onSave(MethodCall call, Result result) {
        final String key = call.argument("key");
        final String value = call.argument("value");

        Log.d("RemotePreferences", "Save called with key "
                + key + " and value " + value);

        SharedPreferences prefs = context.getSharedPreferences(
                CustomBackupAgent.REMOTE_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, value);
        editor.apply();

        CustomBackupAgent.requestBackup(context);

        result.success("Successful saved the data");
    }

    private void onDelete(MethodCall call, Result result) {
         final String key = call.argument("key");

         SharedPreferences prefs = context.getSharedPreferences(
                CustomBackupAgent.REMOTE_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.remove(key);
        editor.apply();

        CustomBackupAgent.requestBackup(context);

        final String log = "Key " + key + " successful deleted";

        Log.d("RemotePreferences", log);

        result.success(log);
    }

     private void onDeleteAll(Result result) {
         SharedPreferences prefs = context.getSharedPreferences(
                 CustomBackupAgent.REMOTE_PREFERENCES_KEY, Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = prefs.edit();

         editor.clear();
         editor.apply();

         CustomBackupAgent.requestBackup(context);

         final String log = "The collection "
                 + CustomBackupAgent.REMOTE_PREFERENCES_KEY + " has been cleared";

         Log.d("RemotePreferences", log);

         result.success(log);
    }
}