const {create} = require('xmlbuilder2');

const isVersion = key => key === 'version' || key === 'Version';
const isSpring = key => key.indexOf("org.springframework.bootspring-boot-starter-parent") != -1;
const isProject = key => key === 'project';
const isParent = key => key === 'parent';

const readVersion = contents => {
    const doc = create(contents);
    const versionNode = doc.find(n => {
        if (isVersion(n.node.nodeName)) {
            if (isProject(n.up().node.nodeName)) {
                return true;
            }
            if (isParent(n.up().node.nodeName) && !isSpring(n.up().node.textContent)) {
                return true;
            }

        }
    }, true, true);
    return versionNode && versionNode.node.textContent;
};

const writeVersion = (contents, version) => {
    const doc = create(contents);
    doc.find(
        n => {
            if (isVersion(n.node.nodeName)) {
                if (n.up().node.nodeName === 'project') {
                    n.node.textContent = version;
                    return true;
                }
                if (isParent(n.up().node.nodeName) && !isSpring(n.up().node.textContent)) {
                    n.node.textContent = version;
                    return true;
                }
            }
        },
        true,
        true
    );
    return doc.end({prettyPrint: true});
};

module.exports = {
    readVersion,
    writeVersion,
};