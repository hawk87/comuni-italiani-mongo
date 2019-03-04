package it.comuni.comuniitaliani.mapper;

import it.comuni.comuniitaliani.model.Regione;
import it.comuni.comuniitaliani.vm.RegioneVM;
import org.springframework.stereotype.Service;

@Service
public class RegioneMapper implements Mapper<RegioneVM, Regione> {
    @Override
    public RegioneVM dto2vm(Regione dto) {
        final RegioneVM vm = new RegioneVM();
        vm.setCodice(dto.getCodice());
        vm.setNome(dto.getNome());
        return vm;
    }

    @Override
    public Regione vm2dto(RegioneVM vm) {
        final Regione dto = new Regione();
        dto.setCodice(vm.getCodice());
        dto.setNome(vm.getNome());
        return dto;
    }
}
