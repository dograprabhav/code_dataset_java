from dataclasses import dataclass, field
from typing import Optional
from datetime import datetime


class BaseEntity:
    """Base class for all domain entities with common fields."""

    def __init__(self, id: str, created_at: Optional[datetime] = None):
        self.id = id
        self.created_at = created_at or datetime.utcnow()

    def to_dict(self) -> dict:
        return {"id": self.id, "created_at": self.created_at.isoformat()}


@dataclass
class User(BaseEntity):
    """Represents an application user."""

    name: str = ""
    email: str = ""
    role: str = "viewer"
    active: bool = True
    tags: list[str] = field(default_factory=list)

    def __init__(self, id: str, name: str, email: str, role: str = "viewer"):
        super().__init__(id)
        self.name = name
        self.email = email
        self.role = role
        self.active = True
        self.tags = []

    def promote(self, new_role: str) -> None:
        allowed = {"viewer", "editor", "admin"}
        if new_role not in allowed:
            raise ValueError(f"Invalid role: {new_role}")
        self.role = new_role

    def deactivate(self) -> None:
        self.active = False

    def to_dict(self) -> dict:
        base = super().to_dict()
        base.update({"name": self.name, "email": self.email, "role": self.role, "active": self.active})
        return base


@dataclass
class Project(BaseEntity):
    """A project that belongs to an organization."""

    title: str = ""
    description: str = ""
    owner_id: str = ""
    members: list[str] = field(default_factory=list)

    def __init__(self, id: str, title: str, owner_id: str, description: str = ""):
        super().__init__(id)
        self.title = title
        self.owner_id = owner_id
        self.description = description
        self.members = [owner_id]

    def add_member(self, user_id: str) -> bool:
        if user_id in self.members:
            return False
        self.members.append(user_id)
        return True

    def remove_member(self, user_id: str) -> bool:
        if user_id == self.owner_id:
            raise ValueError("Cannot remove the project owner")
        if user_id not in self.members:
            return False
        self.members.remove(user_id)
        return True

    def to_dict(self) -> dict:
        base = super().to_dict()
        base.update({"title": self.title, "owner_id": self.owner_id, "member_count": len(self.members)})
        return base


class TaskStatus:
    OPEN = "open"
    IN_PROGRESS = "in_progress"
    DONE = "done"


class Task:
    """A task within a project."""

    def __init__(self, id: str, title: str, project_id: str, assignee_id: Optional[str] = None):
        self.id = id
        self.title = title
        self.project_id = project_id
        self.assignee_id = assignee_id
        self.status = TaskStatus.OPEN

    def assign(self, user_id: str) -> None:
        self.assignee_id = user_id
        if self.status == TaskStatus.OPEN:
            self.status = TaskStatus.IN_PROGRESS

    def complete(self) -> None:
        self.status = TaskStatus.DONE

    def reopen(self) -> None:
        self.status = TaskStatus.OPEN
