package viewmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KitchenCompletedViewModel implements Serializable {

    private List<KitchenRowViewModel> rows = new ArrayList<>();
    private int count;

    public KitchenCompletedViewModel() {}

    public List<KitchenRowViewModel> getRows() { return rows; }
    public void setRows(List<KitchenRowViewModel> rows) { this.rows = rows; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
