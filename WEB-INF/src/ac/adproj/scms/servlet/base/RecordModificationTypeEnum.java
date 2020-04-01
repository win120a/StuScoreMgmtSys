package ac.adproj.scms.servlet.base;

public enum RecordModificationTypeEnum {
    ADD("add"), MODIFY("modify");

    private String reqType;

    RecordModificationTypeEnum(String type) {
        this.reqType = type;
    }

    public static RecordModificationTypeEnum getEnumByTypeName(String type) {
        return Enum.valueOf(RecordModificationTypeEnum.class, type.toUpperCase());
    }
}
