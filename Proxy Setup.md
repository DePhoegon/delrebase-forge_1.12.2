# Proxy Setup for Minecraft Asset Downloads

This project includes proxy configuration to handle Minecraft asset downloads through Fiddler Classic, which allows redirecting HTTP requests to HTTPS for `resources.download.minecraft.net`.

## Problem
Minecraft Forge's asset download system tries to use HTTP URLs for `resources.download.minecraft.net`, but the site has been updated to only support HTTPS. This causes asset downloads to fail during the `getAssets` Gradle task and when running `runClient`.

## Solution
Use Fiddler Classic as a proxy to automatically redirect HTTP requests to HTTPS.

### Setup Instructions

#### 1. Download and Install Fiddler Classic
- Download Fiddler Classic from: https://www.telerik.com/fiddler/fiddler-classic
- Install and run Fiddler Classic

#### 2. Configure Fiddler Classic Rules
1. In Fiddler Classic, go to **Rules** → **Customize Rules...**
2. This will open the CustomRules.js file in an editor
3. Find the `static function OnBeforeRequest(oSession: Session)` function
4. Add the following code at the top of the function (inside the opening brace):

```javascript
if (
    oSession.HostnameIs("resources.download.minecraft.net")
    && oSession.fullUrl.StartsWith("http://")
) {
    oSession.fullUrl = oSession.fullUrl.Replace("http://", "https://");
}
```

5. Save the file and restart Fiddler Classic

#### 3. Gradle Proxy Configuration
The project's `gradle.properties` file already includes the necessary proxy settings:

```properties
# Proxy settings for Gradle (Fiddler Classic on Windows)
# Used for connecting to Mojang servers through Fiddler for asset downloading.
systemProp.http.proxyHost=127.0.0.1
systemProp.http.proxyPort=8888
systemProp.http.nonProxyHosts=localhost|127.0.0.1
```

#### 4. Usage
1. Start Fiddler Classic first
2. Run your Gradle tasks that need assets:
   - `./gradlew getAssets` - Downloads just the assets
   - `./gradlew runClient` - Includes asset download as part of setup

### Important Notes

- **Project Cleaning**: If this proxy setup was added after the project was initially set up, you may need to:
  1. Clean the project: `./gradlew clean`
  2. Refresh/resync the project in your IDE
  3. Run `./gradlew getAssets` to download assets with the new proxy configuration

- **Fiddler Must Be Running**: Fiddler Classic must be running before executing any Gradle tasks that download assets, otherwise the connection will fail.

- **Alternative Tasks**: While `getAssets` is specifically for downloading assets, the `runClient` task will also download assets automatically as part of its setup process.

### Troubleshooting

- If assets still fail to download, verify Fiddler is capturing traffic (you should see requests in the Fiddler session list)
- Ensure the CustomRules.js modification was saved and Fiddler was restarted
- Check that no other proxy software is interfering with the connection
