import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import PageContainer from "../../components/PageContainer";

interface Props {

}

// todo add description
// todo display time of last scan
// todo add button to trigger scan
const InitiateScan: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer>
      <Box>
        Initiate scan
      </Box>
    </PageContainer>
  );
};

export default InitiateScan;