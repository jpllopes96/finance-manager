package br.com.jplopes.financeiro.entities.deal;

public enum Type {
    ENTRADA(0,"ENTRADA"),
    SAIDA(1,"SAIDA");

    private Integer code;
    private String description;

    Type(Integer code, String description){
        this.code = code;
        this.description =description;
    }


    public String getDescription(){
        return description;
    }

    public Integer getCode(){
        return code;
    }

    public static Type toEnum(Integer code){
        if(code == null){
            return null;
        }
        for(Type x : Type.values()){
            if (code.equals(x.getCode())){
                return x;
            }
        }
        throw new IllegalArgumentException("Tipo invalido, escolha Entrada ou saida");
    }

}
