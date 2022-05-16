import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import {Divider, Grid, Stack} from "@mui/material";
import SubHeading from "../../../../components/Text/SubHeading";
import BodyText from "../../../../components/Text/BodyText";
import CenteredBox from "../../../../components/CenteredBox";
import {COLOURS} from "../../../../constants/colours";
import Heading from "../../../../components/Text/Heading";

interface Props {

}

const SummaryInfo: FunctionComponent<Props> = (props) => {

  return (
    <Stack>
      <Box sx={{p: 2, borderBottom: 1}}>
        <CenteredBox>
          <SubHeading center> 10/05/22 11:00PM </SubHeading>
        </CenteredBox>
      </Box>
      <Box sx={{p: 2}}>
        <Stack>
          <BodyText bold>Vulnerabilities</BodyText>
          <Divider/>
          <Box sx={{mt: 2}}/>
          <BodyText style={{background: COLOURS.RED_HIGHLIGHT}}>
            Newly added vulnerabilities: 2
          </BodyText>
          <Box sx={{mt: 1}}/>
          <BodyText style={{background: COLOURS.YELLOW_HIGHLIGHT}}>
            Unresolved existing vulnerabilities: 12
          </BodyText>
          <Box sx={{mt: 1}}/>
          <BodyText style={{background: COLOURS.GREEN_HIGHLIGHT}}>
            Resolved existing vulnerabilities: 2
          </BodyText>
        </Stack>
        <Box sx={{mt: 2}}/>
        <BodyText bold>Impact</BodyText>
        <Divider/>
        <Stack direction={"row"} sx={{py: 2}}>
          <Box sx={{width: "100%"}}>
            <Stack>
              <CenteredBox>
                <Heading>1/2</Heading>
              </CenteredBox>
              <CenteredBox>
                <BodyText> Projects affected</BodyText>
              </CenteredBox>
            </Stack>

            {/*<BodyText> 3 projects are affected</BodyText>*/}
          </Box>
          <Box sx={{width: "100%"}}>
            <Stack>
              <CenteredBox>
                <Heading>3/4</Heading>
              </CenteredBox>
              <CenteredBox>
                <BodyText> Repositories affected</BodyText>
              </CenteredBox>
            </Stack>
          </Box>
        </Stack>
      </Box>
    </Stack>
  );
};

export default SummaryInfo;