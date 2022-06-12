lfp
lal
cfp
push 0
lir1 2
swfp 0
lir1 2
lr1
lfp
sal
lw1 0
sr2
bless label2
lir1 0
b label3
label2:
lir1 1
label3:
lir2 1
beq label0
lfp
lal
cfp
lir1 1
lr1
lfp
sal
lw 0
lw1 0
sr2
sub
print
sal
sfp
b label1
label0:
lfp
lal
cfp
lfp
sal
lw 0
lw1 0
print
sal
sfp
label1:
pop
sal
sfp
halt