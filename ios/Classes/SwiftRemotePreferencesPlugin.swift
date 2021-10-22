import Flutter
import UIKit
import CloudKit

public class SwiftRemotePreferencesPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "remote_preferences", binaryMessenger: registrar.messenger())
    let instance = SwiftRemotePreferencesPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "save":
        if let arguments = call.arguments as? Dictionary<String, Any>,
           let key = arguments["key"] as? String,
           let value = arguments["value"] as? String {
                var keyStore = NSUbiquitousKeyValueStore()
                
                keyStore.set(value, forKey: key)

                keyStore.synchronize()
                
                result("Successful saved the data")
         } else {
            result(FlutterError.init(code: "Error", message: "The parameter key or value is missing", details: nil))
         }
        
    case "get":
        if let arguments = call.arguments as? Dictionary<String, Any>,
           let key = arguments["key"] as? String {
                var keyStore = NSUbiquitousKeyValueStore()

                let value = keyStore.string(forKey: key)

                result(value)
         } else {
            result(FlutterError.init(code: "Error", message: "The parameter key is missing", details: nil))
         }
        
    case "delete":
        if let arguments = call.arguments as? Dictionary<String, Any>,
           let key = arguments["key"] as? String {
                var keyStore = NSUbiquitousKeyValueStore()
            
                keyStore.removeObject(forKey: key)
            
                keyStore.synchronize()

                result("Key successful deleted")
         } else {
            result(FlutterError.init(code: "Error", message: "The parameter key is missing", details: nil))
         }
        
    case "deleteAll":
        var keyStore = NSUbiquitousKeyValueStore()
            
        let values = keyStore.dictionaryRepresentation
    
        values.keys.forEach { key in
            keyStore.removeObject(forKey: key)
        }
    
        keyStore.synchronize()

        result("All keys successful deleted")
        
    default:
        result(FlutterError.init(code: "Error", message: "Not implemented", details: nil))
    }
  }
}
