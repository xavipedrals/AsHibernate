package taules;

import javax.persistence.*;


@Entity
@IdClass(CasellaPK.class)
public class Casella {
    private int idpartida;
    private int numerofila;
    private int numerocolumna;
    private Integer numero;

    /**Clau forana**/
    private Partida partida;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idpartida", insertable = false, updatable = false)
    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    /**Fi clau forana**/

    @Id
    @Column(name = "idpartida")
    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
    }

    @Id
    @Column(name = "numerofila", nullable = false)
    public int getNumerofila() {
        return numerofila;
    }

    public void setNumerofila(int numerofila) {
        this.numerofila = numerofila;
    }

    @Id
    @Column(name = "numerocolumna", nullable = false)
    public int getNumerocolumna() {
        return numerocolumna;
    }

    public void setNumerocolumna(int numerocolumna) {
        this.numerocolumna = numerocolumna;
    }

    @Basic
    @Column(name = "numero")
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Casella casella = (Casella) o;

        if (idpartida != casella.idpartida) return false;
        if (numerofila != casella.numerofila) return false;
        if (numerocolumna != casella.numerocolumna) return false;
        if (numero != null ? !numero.equals(casella.numero) : casella.numero != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idpartida;
        result = 31 * result + numerofila;
        result = 31 * result + numerocolumna;
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }
}
