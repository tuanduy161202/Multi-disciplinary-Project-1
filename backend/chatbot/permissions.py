from rest_framework import permissions
        

class IsIntentOwner(permissions.DjangoModelPermissions):
    def has_permission(self, request, view):
        if request.user.is_authenticated:
            return super().has_permission(request, view)
        return False

    def has_object_permission(self, request, view, obj):
        if request.user.house is None:
            return False
        user_house = request.user.house
        obj_house  = obj.device.house
        return user_house == obj_house


class IsCommandOwner(permissions.DjangoModelPermissions):
    def has_permission(self, request, view):
        if request.user.is_authenticated:
            return super().has_permission(request, view)
        return False

    def has_object_permission(self, request, view, obj):
        if request.user.house is None:
            return False
        user_house = request.user.house
        obj_house  = obj.house
        return user_house == obj_house
        