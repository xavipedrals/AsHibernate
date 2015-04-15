package taules;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by xavivaio on 15/04/2015.
 */
@Entity
public class Partida {
    private int idpartida;
    private boolean estaacabada = false;
    private boolean estaguanyada = false;
    private int puntuacio;

    @Id
    @Column(name = "idpartida")
    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
    }

    @Basic
    @Column(name = "estaacabada")
    public boolean isEstaacabada() {
        return estaacabada;
    }

    public void setEstaacabada(boolean estaacabada) {
        this.estaacabada = estaacabada;
    }

    @Basic
    @Column(name = "estaguanyada")
    public boolean isEstaguanyada() {
        return estaguanyada;
    }

    public void setEstaguanyada(boolean estaguanyada) {this.estaguanyada = estaguanyada;
    }

    @Basic
    @Column(name = "puntuacio")
    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partida partida = (Partida) o;

        if (idpartida != partida.idpartida) return false;
        if (estaacabada != partida.estaacabada) return false;
        if (estaguanyada != partida.estaguanyada) return false;
        if (puntuacio != partida.puntuacio) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idpartida;
        result = 31 * result + (estaacabada ? 1 : 0);
        result = 31 * result + (estaguanyada ? 1 : 0);
        result = 31 * result + puntuacio;
        return result;
    }
}
