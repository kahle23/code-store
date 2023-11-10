package store.code.extension.sms.way1;

import artoria.data.AbstractExtraData;

import java.io.Serializable;

/**
 * Short messaging service send result.
 * @author Kahle
 */
public class SmsSendResult extends AbstractExtraData implements Serializable {
    /**
     * Error code.
     */
    private String code;
    /**
     * Description of code.
     */
    private String description;
    /**
     * Business id.
     */
    private String businessId;

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getBusinessId() {

        return businessId;
    }

    public void setBusinessId(String businessId) {

        this.businessId = businessId;
    }

}