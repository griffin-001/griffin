import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import {Divider, Grid, Stack} from "@mui/material";
import SubHeading from "../../../../components/Text/SubHeading";
import BodyText from "../../../../components/Text/BodyText";
import CenteredBox from "../../../../components/CenteredBox";
import {COLOURS} from "../../../../constants/colours";
import Heading from "../../../../components/Text/Heading";

interface Props {
  dateTime: Date;
  summary: VulnerabilitySummary;
}

const SummaryInfo: FunctionComponent<Props> = (props) => {

  return (
    <Stack>
      <Box sx={{p: 2, borderBottom: 1}}>
        <CenteredBox>
          {/*todo we will need to format this more*/}
          <SubHeading center> {props.dateTime.toString()} </SubHeading>
        </CenteredBox>
      </Box>
      <Box sx={{p: 2}}>
        <Stack>
          <BodyText bold>Vulnerabilities</BodyText>
          <Divider/>
          <Box sx={{mt: 2}}/>
          <BodyText style={{background: COLOURS.RED_HIGHLIGHT}}>
            Newly added vulnerabilities: {props.summary.newVulnerabilities}
          </BodyText>
          <Box sx={{mt: 1}}/>
          <BodyText style={{background: COLOURS.YELLOW_HIGHLIGHT}}>
            Unresolved existing
            vulnerabilities: {props.summary.unresolvedExistingVulnerabilities}
          </BodyText>
          <Box sx={{mt: 1}}/>
          <BodyText style={{background: COLOURS.GREEN_HIGHLIGHT}}>
            Resolved existing
            vulnerabilities: {props.summary.resolvedExistingVulnerabilities}
          </BodyText>
        </Stack>
        <Box sx={{mt: 2}}/>
        <BodyText bold>Impact</BodyText>
        <Divider/>
        <Stack direction={"row"} sx={{py: 2}}>
          <Box sx={{width: "100%"}}>
            <Stack>
              <CenteredBox>
                <Heading>{props.summary.projectsAffected}/{props.summary.totalProjects}</Heading>
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
                <Heading>{props.summary.repositoriesAffected}/{props.summary.totalRepositories}</Heading>
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