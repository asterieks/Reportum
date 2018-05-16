import {User} from "../../admin/admin.component";
export class Project{
    projectId: number;
    projectName: string;
    reporter: User;
    teamLeader: User;
    manager: User;
    state: string;
    stateDate: Date;
    actual: boolean;
}
