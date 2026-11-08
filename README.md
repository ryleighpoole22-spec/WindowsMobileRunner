# Windows Mobile Runner — Android prototype

A starter Android Studio project targeting Android 9+ (including the Google Pixel 9).

## Included
- Android launcher interface
- `.exe` file picker
- Winlator hand-off (if Winlator is installed)
- Basic source editor with Python and C templates
- Save source files through Android's Storage Access Framework
- GitHub Actions workflow to build a debug APK

## Build an APK
### Easiest: GitHub Actions
1. Create a GitHub repository and upload this project.
2. Open **Actions** and run **Build Android APK** (or push to `main`/`master`).
3. Download the `WindowsMobileRunner-debug` artifact from the completed workflow.
4. Extract `app-debug.apk` and install it on the Pixel 9.

### Android Studio
1. Open this folder in Android Studio.
2. Install Android SDK Platform 35 and Android Build Tools 35.
3. Sync Gradle and run **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Windows app runtime
This project does not contain a Windows emulator. The EXE picker cannot execute Windows binaries directly. It can hand off to the separately installed Winlator app, whose compatibility varies by application and device. Pixel 9 uses a Google Tensor chipset; do not assume Snapdragon-specific graphics paths will work.

Winlator project: https://github.com/brunodev85/Winlator

## Programming tools
The editor currently edits and saves source text only. It does not compile or run code yet. A practical Android setup is to install Termux separately for native command-line programming:
https://github.com/termux/termux-app

## Important limitations
- No bundled Wine/Box64 runtime.
- No Windows filesystem/container.
- No integrated compiler, debugger, or terminal yet.
- No background Windows services or full desktop session.
- This is a prototype, not a finished all-in-one Windows environment.

Do not install untrusted EXE or APK files. Only use software from sources you trust.
