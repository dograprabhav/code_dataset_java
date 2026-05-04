from typing import Optional
from src.models import User, Project, Task


class UserService:
    """Manages user lifecycle and lookups."""

    def __init__(self):
        self._users: dict[str, User] = {}

    def create_user(self, id: str, name: str, email: str, role: str = "viewer") -> User:
        if id in self._users:
            raise ValueError(f"User {id} already exists")
        user = User(id=id, name=name, email=email, role=role)
        self._users[id] = user
        return user

    def get_user(self, id: str) -> Optional[User]:
        return self._users.get(id)

    def list_active_users(self) -> list[User]:
        return [u for u in self._users.values() if u.active]

    def deactivate_user(self, id: str) -> bool:
        user = self._users.get(id)
        if not user:
            return False
        user.deactivate()
        return True


class ProjectService:
    """Manages projects and membership."""

    def __init__(self, user_service: UserService):
        self._projects: dict[str, Project] = {}
        self._user_service = user_service

    def create_project(self, id: str, title: str, owner_id: str) -> Project:
        if not self._user_service.get_user(owner_id):
            raise ValueError(f"Owner {owner_id} does not exist")
        project = Project(id=id, title=title, owner_id=owner_id)
        self._projects[id] = project
        return project

    def get_project(self, id: str) -> Optional[Project]:
        return self._projects.get(id)

    def add_member(self, project_id: str, user_id: str) -> bool:
        project = self._projects.get(project_id)
        if not project:
            raise ValueError(f"Project {project_id} not found")
        if not self._user_service.get_user(user_id):
            raise ValueError(f"User {user_id} not found")
        return project.add_member(user_id)

    def get_user_projects(self, user_id: str) -> list[Project]:
        return [p for p in self._projects.values() if user_id in p.members]


def build_project_summary(project: Project, user_service: UserService) -> dict:
    """Build a rich summary of a project with resolved member names."""
    members = []
    for uid in project.members:
        user = user_service.get_user(uid)
        members.append({"id": uid, "name": user.name if user else "unknown"})
    return {
        "project": project.to_dict(),
        "members": members,
    }
