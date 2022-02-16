import java.util.Objects;

public class PairStringInt {
    private String s;
    private Integer i;

    PairStringInt(String s, Integer i){
        this.s = s;
        this.i = i;
    }

    public Integer getI() {
        return i;
    }

    public String getS() {
        return s;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof PairStringInt))
            return false;

        PairStringInt other = (PairStringInt) o;

        if(this.s != null && this.i != null){
            if(this.s.equals(other.s) && this.i.equals(other.s))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.s.hashCode() * this.i.hashCode();
    }

    @Override
    public String toString() {
        return "PairStringInt{" +
                "s='" + s + '\'' +
                ", i=" + i +
                '}';
    }
}
