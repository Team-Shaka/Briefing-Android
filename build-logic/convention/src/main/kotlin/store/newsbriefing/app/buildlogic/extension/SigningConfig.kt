package store.newsbriefing.app.buildlogic.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import java.util.Properties

internal fun Project.configureSigningConfig(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val keyStoreProperties = Properties().apply {
        load(rootProject.file("keystore.properties").inputStream())
    }

    commonExtension.apply {
        signingConfigs {
            create("config") {
                storeFile = file(keyStoreProperties["STORE_FILE"] as String)
                storePassword = keyStoreProperties["STORE_PASSWORD"] as String
                keyAlias = keyStoreProperties["KEY_ALIAS"] as String
                keyPassword = keyStoreProperties["KEY_PASSWORD"] as String
            }
        }
    }
}