# اليوميات الوجودية (Vvv)

تطبيق Android أصلي بـ Kotlin وJetpack Compose لتسجيل اليوميات وتتبع المزاج، مع اقتباسات وتذكيرات وحفظ محلي.

## المتطلبات

- Android Studio.
- JDK 17.
- Android SDK 35 (minSdk 24).
- Gradle Wrapper 8.9 المرفق مع المشروع.

## التثبيت والتشغيل

```bash
git clone https://github.com/haiderbyte/Vvv.git
cd Vvv
./gradlew assembleDebug
```

أو افتح مجلد المشروع في Android Studio، ثبّت Android SDK 35 من SDK Manager، ثم نفّذ **Sync Project with Gradle Files** واضغط **Run** لتشغيله على محاكي أو جهاز Android.

بعد نجاح بناء debug ستجد APK في `app/build/outputs/apk/debug/app-debug.apk`. أول تشغيل لـ Gradle قد ينزّل التوزيعة والاعتماديات. لم يُتحقق من بناء APK في بيئة الفحص لعدم توفر Android SDK فيها.

لا يوجد تطبيق ويب أو حاجة لمفتاح Gemini؛ تعليمات AI Studio القديمة أزيلت لأنها لا تطابق ملفات المشروع.
