import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import PageContainer from "../../components/PageContainer";

interface Props {

}


// todo add summary info for entire ecosystem
// number of existing vulnerabilities
// number of new vulnerabilities

// potentially could also add a table with
// todo add some summary info for each projectDependencies
//  i.e. number of vulnerabilities
//
const VulnerabilitiesReport: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer>
      <Box>
        Vulnerabilities report
      </Box>
    </PageContainer>

  );
};

export default VulnerabilitiesReport;