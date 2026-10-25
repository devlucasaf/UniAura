package erp.uniaura.services.web.professor.pesquisarProfessores.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarProfessoresProfessorRetornoV1 {

    @Schema(description = "Identificador público do professor")
    private Long        uid;

    @Schema(description = "TRANSITÓRIO: id sequencial, ainda exigido por Estágio, Frequência, TCC e Coordenação; sai quando eles migrarem")
    private Long        id;
    private String      nome;
    private String      email;
    private String      formacao;
    private String      areaAtuacao;
    private Integer     cargaHorariaSemanal;
    private LocalDate   dataAdmissao;
    private Boolean     ativo;
}
