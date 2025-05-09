package br.gov.pe.ses.starter.enums;

import org.hibernate.envers.RevisionType;

public enum TipoRevisao {
	
	INSERCAO("Inserção", RevisionType.ADD,0),
    ATUALIZACAO("Atualização", RevisionType.MOD,1),
    DELECAO("Deleção", RevisionType.DEL,2);

    private final String descricao;
    private final RevisionType revisionType;
    private final Integer ordem;

    TipoRevisao(String descricao, RevisionType revisionType,Integer ordem) {
        this.descricao = descricao;
        this.revisionType = revisionType;
        this.ordem = ordem;
    }

    public String getDescricao() {
        return descricao;
    }

    public static String obterTipoRevisao (RevisionType revisionType) {
        for (TipoRevisao tipo : values()) {
            if (tipo.revisionType == revisionType) {
                return tipo.getDescricao();
            }
        }
        return "DESCONHECIDO";
    }

	public RevisionType getRevisionType() {
		return revisionType;
	}
	
	public static String obterRevisaoOrdem (Integer ordem) {
        for (TipoRevisao tipo : values()) {
            if (tipo.ordem == ordem) {
                return tipo.getDescricao();
            }
        }
        return "DESCONHECIDO";
    }

}
