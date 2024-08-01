plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.crudlibreria"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.crudlibreria"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//buildFeatures{
//    viewBinding = true
//}
//viewBinding {
//    enabled = true
//}

dependencies {

    //procedemos a agregar tres  dependencias para

    // dependencia para hacer peticiones RestFull (hacer peticiones a la api)
    implementation("com.android.volley:volley-cronet:1.2.1")//hacer peticiones a la api

    //para las treas en segundo  hilo: actualizar, eliminar,editar,consultar
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //interpretar la respuesta json(arreglos)
    implementation("com.google.code.gson:gson:2.10.1")// json con g por que esa es la implementacion de google

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ui.desktop)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}