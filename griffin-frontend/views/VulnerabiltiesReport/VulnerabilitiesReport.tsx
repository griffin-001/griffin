import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import PageContainer from "../../components/PageContainer";
import {
    Button,
    Grid,
    Typography
} from "@mui/material";
import VulnerabilitiesSummary from "./VulnerabilitiesSummary";
import VulnerabilitiesListAll from "./VulnerabilitiesListAll";

interface Props {
    vulnerabilities: Array<Vulnerability>,
}


// todo add summary info for entire ecosystem
// number of existing vulnerabilities
// number of new vulnerabilities

// potentially could also add a table with
// todo add some summary info for each projectDependencies
//  i.e. number of vulnerabilities
//
const VulnerabilitiesReport: FunctionComponent<Props> = (props) => {

    const [value,setValue] = useState(false);

    const handleButton = () => {
        value ? setValue(false) : setValue(true);
    }


    return (
        <PageContainer>
            <Box sx={{mt:3, mx:5}}>
                <Grid container spacing = {2}>
                    <Grid item xs ={8}>
                        <Typography variant = "h3"> Vulnerability Report </Typography>
                    </Grid>
                    <Grid item xs = {4} sx={{mt:2}}>
                        <Box display="flex" justifyContent="flex-end">
                            <Button variant="outlined" size ="large"
                                    onClick={(event) =>
                                handleButton()}>Scan</Button>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
            <VulnerabilitiesSummary items = {props.vulnerabilities}></VulnerabilitiesSummary>
            <VulnerabilitiesListAll items = {props.vulnerabilities}></VulnerabilitiesListAll>
        </PageContainer>

    );
};

export default VulnerabilitiesReport;