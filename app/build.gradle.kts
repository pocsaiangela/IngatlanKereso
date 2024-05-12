plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ingatlankereso"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ingatlankereso"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //firebase-google login
    implementation(libs.material.v1110)
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.1.0")

    //pics
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation (libs.glide)
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("androidx.navigation:navigation-fragment:2.2.2")
    implementation ("androidx.navigation:navigation-ui:2.2.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    testImplementation("junit:junit:4.+")

    //firestore
    implementation("com.google.firebase:firebase-firestore-ktx:21.4.3")
    implementation("com.android.support:multidex:1.0.3")
}