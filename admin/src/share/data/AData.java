package share.data;

public abstract class AData implements IData {
    private static final long serialVersionUID = 42L;

    protected int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0)
            this.id = id;
    }
}
