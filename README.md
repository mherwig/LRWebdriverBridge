# LRWebdriverBridge
Utility for running Webdriver tests out of HP LoadRunner scripts allowing you to do performance tests with PhantomJS

1. run ``./gradlew build``
2. place ``actions/bin/actions`` directory containing the compiled webdriver scripts together with ``core/build/libs/LRWebdriverBridge-core-1.0.jar`` into directory of your HP LoadRunner test script
3. add LRWebdriverBridge-core-1.0.jar as extra file to a HP LoadRunner Java Record-Replay test script
4. Import LRWebdriverBridge

Note: LRWebdriverBridge uses the PhantomJS driver so you need to install PhantomJS on the machine where LRWebdriverBridge will be called.
Also note that running PhantomJS scripts directly should be possible with LRWebdriverBridge (``RunnerMode.NATIVE``) but is currently untested.

The most simpliest call of the library would be something like this:
```
LRWebdriverBridge.getInstance().execute("actions.SimpleExampleAction", RunnerMode.WEBDRIVER_API);
```

For more examples have a look at ``example/vugen/LRWebdriverBridgeTest`` which also shows how to register listeners and actually measure time and how to pass parameters to webdriver scripts.

More documentation will follow eventually.
