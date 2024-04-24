import { FaLocationArrow, FaBriefcase, FaCalendarAlt } from "react-icons/fa";
import { Link, Form } from "react-router-dom";
import Wrapper from "../assets/css/Job";
import JobInfo from "./JobInfo";
import day from "dayjs";
import advancedFormat from "dayjs/plugin/advancedFormat";
day.extend(advancedFormat);

type Props = {
    id: string;
    position: string;
    company: string;
    jobLocation: string;
    jobType: string;
    createdAt: string;
    jobStatus: string;
}

const Job = ({ id, position, company, jobLocation, jobType, createdAt, jobStatus }: Props) => {
    const date = day(createdAt).format('MMM Do, YYYY')
    
    return <Wrapper>
        <header>
            <div className="main-icon">{company.charAt(0)}</div>
            <div className="info">
                <h5>{position}</h5>
                <p>{company}</p>
            </div>
        </header>
        <div className="content">
            <div className="content-center">
                <JobInfo icon={<FaLocationArrow />} text={jobLocation} />
                <JobInfo icon={<FaCalendarAlt />} text={date} />
                <JobInfo icon={<FaBriefcase />} text={jobType} />
                <div className={`status ${jobStatus}`}>{jobStatus}</div>
            </div>
            <footer className="actions">
                <Link to={`../edit-job/${id}`} className="btn edit-btn" >Edit</Link>
                <Form method="post" action={`../delete-job/${id}`}>
                    <button type="submit" className="btn delete-btn">
                        Delete
                    </button>
                </Form>
            </footer>
        </div>
    </Wrapper>;
};

export default Job;
