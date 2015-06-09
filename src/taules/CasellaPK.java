package taules;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public class CasellaPK implements Serializable {
    private int idpartida;
    private int numerofila;
    private int numerocolumna;

    @Column(name = "idpartida")
    @Id
    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
    }

    @Column(name = "numerofila")
    @Id
    public int getNumerofila() {
        return numerofila;
    }

    public void setNumerofila(int numerofila) {
        this.numerofila = numerofila;
    }

    @Column(name = "numerocolumna")
    @Id
    public int getNumerocolumna() {
        return numerocolumna;
    }

    public void setNumerocolumna(int numerocolumna) {
        this.numerocolumna = numerocolumna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CasellaPK casellaPK = (CasellaPK) o;

        if (idpartida != casellaPK.idpartida) return false;
        if (numerofila != casellaPK.numerofila) return false;
        if (numerocolumna != casellaPK.numerocolumna) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idpartida;
        result = 31 * result + numerofila;
        result = 31 * result + numerocolumna;
        return result;
    }
}
