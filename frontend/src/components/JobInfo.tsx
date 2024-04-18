import Wrapper from "../assets/wrappers/JobInfo";

type Props = {
    icon: JSX.Element;
    text: string;
};

const JobInfo = ({ icon, text }: Props) => {
    return (
        <Wrapper>
            <span className="job-icon">{icon}</span>
            <span className="job-text">{text}</span>
        </Wrapper>
    );
};

export default JobInfo;
