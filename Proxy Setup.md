# Proxy Setup for Minecraft Asset Downloads

This document explains how to set up a proxy to force HTTPS downloads for Minecraft assets when the Forge Gradle `getAssets` task tries to download from `http://resources.download.minecraft.net/`.

## Problem
The `getAssets` Gradle task downloads Minecraft assets using HTTP URLs, but the server has been updated to require HTTPS. This causes download failures during the build process.

## Solution
Use Fiddler Classic as a local proxy to intercept HTTP requests and redirect them to HTTPS.

## Important Notes Before Setup

⚠️ **Project Cleanup Required**: If you're adding proxy settings to an existing project that was already set up, you'll need to clean and resync the project:

```bash
./gradlew clean
```

After adding the proxy configuration, refresh/resync your IDE project (in IntelliJ: File → Reload Gradle Project).

## Setup Instructions

### 1. Download and Install Fiddler Classic

1. Go to [https://www.telerik.com/fiddler/fiddler-classic](https://www.telerik.com/fiddler/fiddler-classic)
2. Download Fiddler Classic (free version)
3. Install Fiddler Classic with default settings

### 2. Configure Fiddler Classic Rules

1. Open Fiddler Classic
2. Go to **Rules** → **Customize Rules** (or press Ctrl+R)
3. This will open the CustomRules.js file in your default editor
4. Find the function `static function OnBeforeRequest(oSession: Session)`
5. Add the following code at the **top** of the function (right after the opening brace):

```javascript
if (
    oSession.HostnameIs("resources.download.minecraft.net")
    && oSession.fullUrl.StartsWith("http://")
) {
    oSession.fullUrl = oSession.fullUrl.Replace("http://", "https://");
}
```

The function should look like this:
```javascript
static function OnBeforeRequest(oSession: Session) {
    // Add the HTTP to HTTPS redirect rule here
    if (
        oSession.HostnameIs("resources.download.minecraft.net")
        && oSession.fullUrl.StartsWith("http://")
    ) {
        oSession.fullUrl = oSession.fullUrl.Replace("http://", "https://");
    }
    
    // ...existing code...
}
```

6. Save the file and close the editor
7. Fiddler will automatically reload the rules

### 3. Gradle Properties Configuration

The `gradle.properties` file is already configured with the necessary proxy settings:

```properties
# Proxy settings for Gradle (Fiddler Classic on Windows)
# Used for connecting to Mojang servers through Fiddler for asset downloading.
systemProp.http.proxyHost=127.0.0.1
systemProp.http.proxyPort=8888
systemProp.http.nonProxyHosts=localhost|127.0.0.1
```

These settings tell Gradle to route HTTP requests through Fiddler Classic running on localhost port 8888.

### 4. Usage

1. **Start Fiddler Classic** before running any Gradle tasks that download assets
2. Ensure Fiddler is capturing traffic (File → Capture Traffic should be checked)
3. Run your Gradle build command:
   - `./gradlew getAssets` - Downloads assets only
   - `./gradlew runClient` - Downloads assets and runs the client (includes getAssets as part of setup)
   - `./gradlew build` - Full build process
4. Fiddler will intercept the HTTP requests to `resources.download.minecraft.net` and redirect them to HTTPS
5. You can monitor the traffic in Fiddler to verify the redirects are working

**Note**: The `runClient` task automatically includes asset downloading as part of its setup process, so you typically don't need to run `getAssets` separately unless you specifically want to download assets without running the client.

### 5. Verification

To verify the setup is working:
1. Run `./gradlew getAssets` with Fiddler running
2. In Fiddler, you should see requests to `resources.download.minecraft.net`
3. The requests should show as HTTPS (port 443) in the Host column
4. Assets should download successfully without HTTP errors

### 6. Troubleshooting

- **Fiddler not intercepting traffic**: Ensure "Capture Traffic" is enabled in Fiddler
- **Still getting HTTP errors**: Verify the custom rule was saved correctly and Fiddler reloaded the rules
- **Proxy connection errors**: Check that Fiddler is running on port 8888 (default)
- **Certificate errors**: Fiddler may need to be configured to decrypt HTTPS traffic (Tools → Options → HTTPS tab)

### 7. Alternative: System-wide Solution

If you want a more permanent solution, you can also modify your system's hosts file to redirect the domain, but the Fiddler approach is more flexible and easier to toggle on/off as needed.

## Notes

- This setup only affects Gradle builds when Fiddler is running
- The proxy settings only apply to HTTP requests (HTTPS requests bypass the proxy due to the nonProxyHosts setting)
- You can leave Fiddler running in the background during development
- This solution specifically targets the `getAssets` Gradle task and similar asset download operations
- **Important**: If proxy settings were added after initial project setup, always run `./gradlew clean` and resync your IDE project
- The `runClient` task includes asset downloading, so using it will also trigger the same HTTP→HTTPS redirects
