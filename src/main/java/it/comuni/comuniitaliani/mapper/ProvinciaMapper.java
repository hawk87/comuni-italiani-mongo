package it.comuni.comuniitaliani.mapper;

import it.comuni.comuniitaliani.model.Provincia;
import it.comuni.comuniitaliani.vm.ProvinciaVM;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaMapper implements Mapper<ProvinciaVM, Provincia> {
    @Override
    public ProvinciaVM dto2vm(Provincia dto) {
        final ProvinciaVM vm = new ProvinciaVM();
        vm.setCodice(dto.getCodice());
        vm.setNome(dto.getNome());
        return vm;
    }

    @Override
    public Provincia vm2dto(ProvinciaVM vm) {
        final Provincia dto = new Provincia();
        dto.setCodice(vm.getCodice());
        dto.setNome(vm.getNome());
        return dto;
    }
}
