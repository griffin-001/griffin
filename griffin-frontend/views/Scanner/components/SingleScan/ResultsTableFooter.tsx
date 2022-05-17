import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import {Stack} from "@mui/material";
import BodyText from "../../../../components/Text/BodyText";

interface Props {

}

// todo currently unused, have kept this in case it is needed in a later sprint
const ResultsTableFooter: FunctionComponent<Props> = (props) => {

  return (
    <Box display="flex" justifyContent="flex-end" sx={{py: 2}}>
      <Stack>
        <BodyText style={{textAlign: "right"}}>
          Red = Newly added vulnerabilities.
        </BodyText>
        <BodyText style={{textAlign: "right"}}>
          Yellow = Unresolved existing vulnerabilities.
        </BodyText>
        <BodyText style={{textAlign: "right"}}>
          Green = Resolved existing vulnerabilities.
        </BodyText>
      </Stack>
    </Box>
  );
};

export default ResultsTableFooter;