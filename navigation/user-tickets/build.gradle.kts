apply(from = "$rootDir/${CommonBuild.FeatureModule}")

dependencies {
    "api"(project(Feature.USER_TICKETS))
}