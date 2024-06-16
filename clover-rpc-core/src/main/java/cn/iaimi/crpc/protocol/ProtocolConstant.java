package cn.iaimi.crpc.protocol;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/6/12
 */
public interface ProtocolConstant {

    int MESSAGE_HEADER_LENGTH = 17;

    byte PROTOCOL_MAGIC = 0x1;

    byte PROTOCOL_VERSION = 0x1;
}
