inherit autotools pkgconfig systemd linux-kernel-base
require include/common-location-defines.inc

DESCRIPTION = "QTI GPS Location APIs"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/${BP}/LICENSE.txt;md5=724395ab86695d415998c63582feff8c"

SRC_URI = "git://github.com/quic-vthuniki/location-apis-qcom.git;protocol=https;branch=merge_recp_test1;tag=v${PV} \
           file://location_hal_daemon.service \
           file://location_hal_daemon-tmpfilesd.conf \
           "
SRCREV     = "f671ab17ce196654a603564b95fa874509e24076"

S = "${UNPACKDIR}/${BP}"

DEPENDS = "glib-2.0 location-hal protobuf protobuf-native qmi-framework virtual/kernel libcap"
RDEPENDS:${PN} = "location-hal"

EXTRA_OECONF += "--with-systemd"

do_rm_work[noexec] = "1"

do_compile:prepend () {
    echo "Running location_api_msg_protobuf_gen.sh"
    cd ${S}/location_api_msg_proto
    export LD_LIBRARY_PATH="${STAGING_DIR_NATIVE}/usr/lib/x86_64-linux-gnu:${LD_LIBRARY_PATH}"
    bash ./location_api_msg_protobuf_gen.sh
    cd -
}

do_install:append () {
    ## Install systemd-tmpfiles config file
    install -d ${D}${sysconfdir}/tmpfiles.d/
    install -m 0644 ${UNPACKDIR}/location_hal_daemon-tmpfilesd.conf ${D}${sysconfdir}/tmpfiles.d/${BPN}.conf

    ## Install systemd service unit file
    install -d ${D}${sysconfdir}/systemd/system/
    install -m 0644 ${UNPACKDIR}/location_hal_daemon.service -D ${D}${sysconfdir}/systemd/system/location_hal_daemon.service

    if ${@bb.utils.contains('PACKAGECONFIG', 'loc-bootkpi', 'true', 'false', d)}; then
        #Install service files to socket.targets only if dependent boot optmization changes are present.
        # Enable the service for sockets.target
        install -d ${D}${sysconfdir}/systemd/system/sockets.target.wants/
        ln -sf /etc/systemd/system/location_hal_daemon.service \
                ${D}/etc/systemd/system/sockets.target.wants/location_hal_daemon.service
    else
        # Enable the service for multi-user.target
        install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
        ln -sf /etc/systemd/system/location_hal_daemon.service \
                ${D}/etc/systemd/system/multi-user.target.wants/location_hal_daemon.service
    fi
}
