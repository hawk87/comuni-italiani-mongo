package it.comuni.comuniitaliani.mapper;

public interface Mapper<VM, DTO> {
    VM dto2vm(DTO dto);
    DTO vm2dto(VM vm);
}
