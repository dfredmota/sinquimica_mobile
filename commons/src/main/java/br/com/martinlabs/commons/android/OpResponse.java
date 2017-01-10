package br.com.martinlabs.commons.android;

/**
 * Created by gil on 2/19/16.
 */
public class OpResponse<T> {
    private boolean Success; //se obteve sucesso
    private String Message; //mensagem, geralmente usada no erro
    private T Data; //outros detalhes
    private Integer Code;

    public OpResponse() {
        this(true, null);
    }

    public T getData() {
        return Data;
    }

    public void setData(T Data) {
        this.Data = Data;
    }

    public OpResponse(T Data) {
        this.Success = true;
        this.Data = Data;
    }

    public OpResponse(boolean success, String message) {
        this.Success = success;
        this.Message = message;
    }

    public OpResponse(boolean success, String message, T details) {
        this(success, message);
        this.Data = details;
    }

    public boolean isSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public int getCode() {
        if (Code == null) {
            return 0;
        }

        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }
}
