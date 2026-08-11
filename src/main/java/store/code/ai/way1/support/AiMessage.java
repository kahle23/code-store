package store.code.ai.way1.support;

import java.io.Serializable;

/**
 * The ai message. 迁移自 misaka-extend 的 artoria.ai.llm.support.AiMessage 等价类
 * （该 API 在 code-store 锁定的 artoria 版本中不存在，故在 code-store 内自建）。
 * @author Kahle
 */
public class AiMessage implements Serializable {
    private String role;
    private String content;
    private String model;

    public String getRole() {

        return role;
    }

    public void setRole(String role) {

        this.role = role;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

}
