/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nbb.demetra.tutorial.plugin.output.seasonality;

import ec.nbdemetra.sa.output.AbstractOutputNode;
import ec.nbdemetra.ui.properties.NodePropertySetBuilder;
import ec.tss.sa.ISaOutputFactory;
import java.util.List;
import org.openide.nodes.Sheet;

/**
 *
 * @author pcuser
 */
public class CsvSeasonalityNode extends AbstractOutputNode<CsvSeasonalityOutputConfiguration> {


    public CsvSeasonalityNode() {
        super(new CsvSeasonalityOutputConfiguration());
        setDisplayName(CsvSeasonalityOutputFactory.NAME);
    }
    
    public CsvSeasonalityNode(CsvSeasonalityOutputConfiguration config) {
        super(config);
        setDisplayName(CsvSeasonalityOutputFactory.NAME);
    }

    @Override
    protected Sheet createSheet() {
        CsvSeasonalityOutputConfiguration config = getLookup().lookup(CsvSeasonalityOutputConfiguration.class);
        Sheet sheet = super.createSheet();
        NodePropertySetBuilder builder = new NodePropertySetBuilder();
        
        builder.reset("Location");
        builder.withFile().select(config, "Folder").directories(true).description("Base output folder. Will be extended by the workspace and processing names").add();
        builder.with(String.class).select(config, "fileName").display("File name").add();
        sheet.put(builder.build());
 
        
        builder.reset("Content");
        builder.with(List.class).select(config, "series").editor(Series.class).add();
        sheet.put(builder.build());
        builder.with(List.class).select(config, "tests").editor(Tests.class).add();
        sheet.put(builder.build());
        builder.withInt().select(config, "last").display("Last years").add();
        sheet.put(builder.build());
        builder.withInt().select(config, "detail").display("Output detail [0,3]").add();
        sheet.put(builder.build());
        return sheet;
     }

    @Override
    public ISaOutputFactory getFactory() {
        return new CsvSeasonalityOutputFactory(getLookup().lookup(CsvSeasonalityOutputConfiguration.class));
    }
    
}
