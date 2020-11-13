import com.visma.of.solvertemplate.constants.Constants;
import com.visma.of.solvertemplate.solver.SolvertemplateSolver;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class FeaturFlagTest {
    /**
     *
     * Created by martin.sommerseth on 03/11/2020
     *
     *   This class tests that it is able to retrieve default feature flags
     *
     */

    @Test
    public void test(){
        SolvertemplateSolver solvertemplateSolver = new SolvertemplateSolver();
        Map<String, Boolean> defaultFlags = solvertemplateSolver.getSolverFeatureFlagDefaultValues();
        Assert.assertFalse(defaultFlags.get(Constants.OF_TEST_FLAG));
    }

}
