package info.makeyourpicks.web.dataprovider;

import org.apache.wicket.model.LoadableDetachableModel;

import com.delesio.model.AbstractPersistantObject;

public abstract class AbstractLoadableDetachableModel extends LoadableDetachableModel {
    protected long id;

    // constructor
    public AbstractLoadableDetachableModel(AbstractPersistantObject object) {
        super(object);
        this.id = object.getId();            
    }

    // constructor
    public AbstractLoadableDetachableModel(long id) {
        this.id = id;
    }

    @Override
    protected abstract Object load();

    // getters
    public long getId() {
        return id;
    }
    
}