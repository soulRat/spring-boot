// .versionrc.js
const xmlUpdater = require('@soul-rat/standard-version-updater-pom');

const xmlTracker1 = {
    filename: 'pom.xml',
    updater: xmlUpdater,
};

const xmlTracker2 = {
    filename: 'spring-web/pom.xml',
    updater: xmlUpdater,
};

const xmlTracker3 = {
    filename: 'spring-biz/pom.xml',
    updater: xmlUpdater,
};

const xmlTracker4 = {
    filename: 'spring-common/pom.xml',
    updater: xmlUpdater,
};

const xmlTracker5 = {
    filename: 'spring-dal/pom.xml',
    updater: xmlUpdater,
};

module.exports = {
    packageFiles: [xmlTracker1, xmlTracker2, xmlTracker3, xmlTracker4, xmlTracker5],
    bumpFiles: [xmlTracker1, xmlTracker2, xmlTracker3, xmlTracker4, xmlTracker5],
};